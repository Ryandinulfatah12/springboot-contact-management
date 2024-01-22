package dinulfatahryan.belajar.springboot.restfull.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dinulfatahryan.belajar.springboot.restfull.api.entity.Contact;
import dinulfatahryan.belajar.springboot.restfull.api.entity.User;
import dinulfatahryan.belajar.springboot.restfull.api.model.*;
import dinulfatahryan.belajar.springboot.restfull.api.repository.ContactRepository;
import dinulfatahryan.belajar.springboot.restfull.api.repository.UserRepository;
import dinulfatahryan.belajar.springboot.restfull.api.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setName("Rayen");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setUsername("ray");
        user.setToken("ray");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000);
        userRepository.save(user);
    }

    @Test
    void createContactFailed() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateContactFailed() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                put("/api/contacts/12121")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createContactSuccess() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Rayen");
        request.setLastName("Dinul fatah");
        request.setPhone("81326483243");
        request.setEmail("ray@mail.cok");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("Rayen", response.getData().getFirstName());
            assertEquals("Dinul fatah", response.getData().getLastName());
            assertEquals("81326483243", response.getData().getPhone());
            assertEquals("ray@mail.cok", response.getData().getEmail());
            assertTrue(contactRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void updateContactSuccess() throws Exception {
        User user = userRepository.findById("ray").orElseThrow();
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("Rayen");
        contact.setLastName("Dinul fatah");
        contact.setPhone("81326483243");
        contact.setEmail("ray@mail.cok");
        contactRepository.save(contact);

        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Rayen");
        request.setLastName("Dinul UPDATE");
        request.setPhone("00000000");
        request.setEmail("ray@mail.cok");

        mockMvc.perform(
                put("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(request.getEmail(), response.getData().getEmail());
            assertEquals(request.getFirstName(), response.getData().getFirstName());
            assertEquals(request.getLastName(), response.getData().getLastName());
            assertEquals(request.getPhone(), response.getData().getPhone());
            assertTrue(contactRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void getContactFailed() throws Exception {
        mockMvc.perform(
                get("/api/contacts/563324234823")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getContactSuccess() throws Exception {
        User user = userRepository.findById("ray").orElseThrow();
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("Rayen");
        contact.setLastName("Dinul fatah");
        contact.setPhone("81326483243");
        contact.setEmail("ray@mail.cok");
        contactRepository.save(contact);

        mockMvc.perform(
                get("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(contact.getId(), response.getData().getId());
            assertEquals(contact.getEmail(), response.getData().getEmail());
            assertEquals(contact.getFirstName(), response.getData().getFirstName());
            assertEquals(contact.getLastName(), response.getData().getLastName());
            assertEquals(contact.getPhone(), response.getData().getPhone());
        });
    }

    @Test
    void deleteContactFailed() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                delete("/api/contacts/21363294")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void deleteContactSuccess() throws Exception {
        User user = userRepository.findById("ray").orElseThrow();
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstName("Rayen");
        contact.setLastName("Dinul fatah");
        contact.setPhone("81326483243");
        contact.setEmail("ray@mail.cok");
        contactRepository.save(contact);

        mockMvc.perform(
                delete("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
        });
    }

    @Test
    void searchNotFound() throws Exception {
        mockMvc.perform(
                get("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(0, response.getData().size());
            assertEquals(0, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });
    }

    @Test
    void searchSuccess() throws Exception {
        User user = userRepository.findById("ray").orElseThrow();

        for (int i = 0; i < 100; i++) {
            Contact contact = new Contact();
            contact.setId(UUID.randomUUID().toString());
            contact.setUser(user);
            contact.setFirstName("Rayen " + i);
            contact.setLastName("Dinul fatah");
            contact.setPhone("81326483243");
            contact.setEmail("ray@mail.cok"+i);
            contactRepository.save(contact);
        }

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("name", "Rayen")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });


        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("name", "Dinul fatah")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("email", "@mail.cok")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("phone", "813264")
                        .queryParam("page", "1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(0, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(1000, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });


    }
}
