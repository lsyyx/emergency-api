import com.example.data.db.IncidentsTable
import com.example.data.db.Status
import com.example.data.db.Type
import com.example.data.network.api.ApiServiceImpl
import com.example.data.network.dto.IncidentDto
import com.example.data.network.dto.ZoneDto
import com.example.data.repository.RepositoryImpl
import com.example.module
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import io.ktor.test.dispatcher.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiClientTest {

    private var JSON = Json
    private var testApp: TestApplication = TestApplication {
        application { module() }
    }

    @Test
    fun `WHEN get root endpoint THEN return Hello World`() = testSuspend {
        testApp.client.get("/").apply {
            status shouldBe HttpStatusCode.OK
            bodyAsText() shouldBe "Hello, world!"
        }
    }

    @Test
    fun `WHEN get zones endpoint THEN return expected number of zones`() = testSuspend {
        testApp.client.get("/zones").apply {
            val zones = JSON.decodeFromString<List<ZoneDto>>(bodyAsText())
            status shouldBe HttpStatusCode.OK
            zones.size shouldBe 5
        }
    }

    @Test
    fun `WHEN get incidents endpoint THEN return expected incident information`() = testSuspend {
        testApp.client.get("/incidents").apply {
            val incidents = JSON.decodeFromString<List<IncidentDto>>(bodyAsText())
            val expectedIncident = IncidentDto(
                incidentId = 1,
                type = Type.FIRE,
                latitude = 10.0,
                longitude = 10.0,
                description = "Fire incident in area.",
                status = Status.NEW,
                zoneId = 1
            )
            status shouldBe HttpStatusCode.OK
            incidents[0] shouldBe expectedIncident
        }
    }

    @Test
    fun `WHEN create incident endpoint THEN insert successfully`() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val newIncident = IncidentDto(
            incidentId = null,
            type = Type.GASLEAK,
            latitude = 0.0,
            longitude = 0.0,
            description = "Gas leak incident in area.",
            status = Status.NEW,
            zoneId = null
        )
        val response = client.post("/incidents/create")
        {
            contentType(ContentType.Application.Json)
            setBody(newIncident)
        }
        response.status shouldBe HttpStatusCode.Created
        response.bodyAsText() shouldBe "Incident created successfully"
        transaction {
            IncidentsTable.deleteWhere {
                (latitude eq newIncident.latitude) and
                        (longitude eq newIncident.longitude)
            }
        }
    }

    @Test
    fun `WHEN update incident endpoint THEN insert successfully`() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val newIncident = IncidentDto(
            incidentId = 1,
            type = Type.FIRE,
            latitude = 10.0,
            longitude = 10.0,
            description = "Fire incident in area.",
            status = Status.NEW,
            zoneId = 1
        )
        val response = client.post("/incidents/update")
        {
            contentType(ContentType.Application.Json)
            setBody(newIncident)
        }
        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldBe "Incident updated successfully"
    }
}

