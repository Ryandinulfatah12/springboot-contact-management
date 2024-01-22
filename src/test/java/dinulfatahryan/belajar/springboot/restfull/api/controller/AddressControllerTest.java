package dinulfatahryan.belajar.springboot.restfull.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dinulfatahryan.belajar.springboot.restfull.api.entity.Address;
import dinulfatahryan.belajar.springboot.restfull.api.entity.Contact;
import dinulfatahryan.belajar.springboot.restfull.api.entity.User;
import dinulfatahryan.belajar.springboot.restfull.api.model.*;
import dinulfatahryan.belajar.springboot.restfull.api.repository.AddressRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setName("Rayen");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setUsername("ray");
        user.setToken("ray");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("ray");
        contact.setUser(user);
        contact.setFirstName("Rayen");
        contact.setLastName("Dinul fatah");
        contact.setPhone("81326483243");
        contact.setEmail("ray@mail.cok");
        contactRepository.save(contact);
    }

    @Test
    void createAddressBadRequest() throws Exception {
        CreateAddressRequest request = new CreateAddressRequest();
        request.setCountry("");
        request.setProvince("");

        mockMvc.perform(
                post("/api/contacts/ray/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateAddressBadRequest() throws Exception {
        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCountry("");
        request.setProvince("");

        mockMvc.perform(
                put("/api/contacts/ray/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createAddressSuccess() throws Exception {
        CreateAddressRequest request = new CreateAddressRequest();
        request.setCountry("Indonesia");
        request.setStreet("Jalan");
        request.setCity("Legok");
        request.setPostalCode("74834");
        request.setProvince("Banten");

        mockMvc.perform(
                post("/api/contacts/ray/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(request.getStreet(), response.getData().getStreet());
            assertEquals(request.getCity(), response.getData().getCity());
            assertEquals(request.getProvince(), response.getData().getProvince());
            assertEquals(request.getCountry(), response.getData().getCountry());
            assertEquals(request.getPostalCode(), response.getData().getPostalCode());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void updateAddressSuccess() throws Exception {
        Contact contact = contactRepository.findById("ray").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setCountry("LAMA");
        address.setStreet("ALAM");
        address.setCity("LAMA");
        address.setPostalCode("LAMA");
        address.setContact(contact);
        address.setProvince("LAMA");
        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCountry("Indonesia");
        request.setStreet("Jalan");
        request.setCity("Legok");
        request.setPostalCode("74834");
        request.setProvince("Banten");

        mockMvc.perform(
                put("/api/contacts/ray/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(request.getStreet(), response.getData().getStreet());
            assertEquals(request.getCity(), response.getData().getCity());
            assertEquals(request.getProvince(), response.getData().getProvince());
            assertEquals(request.getCountry(), response.getData().getCountry());
            assertEquals(request.getPostalCode(), response.getData().getPostalCode());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void listAddressNotFound() throws Exception {
        mockMvc.perform(
                get("/api/contacts/notfound/addresses")
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
    void getAddressNotFound() throws Exception {
        mockMvc.perform(
                get("/api/contacts/ray/addresses/notfound")
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
    void getAddressSuccess() throws Exception {
        Contact contact = contactRepository.findById("ray").orElseThrow();
        Address address = new Address();
        address.setId("test");
        address.setCountry("Indonesia");
        address.setStreet("Jalan");
        address.setCity("Legok");
        address.setPostalCode("74834");
        address.setContact(contact);
        address.setProvince("Banten");
        addressRepository.save(address);

        mockMvc.perform(
                get("/api/contacts/ray/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(address.getStreet(), response.getData().getStreet());
            assertEquals(address.getCity(), response.getData().getCity());
            assertEquals(address.getProvince(), response.getData().getProvince());
            assertEquals(address.getCountry(), response.getData().getCountry());
            assertEquals(address.getPostalCode(), response.getData().getPostalCode());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void listAddressSuccess() throws Exception {
        Contact contact = contactRepository.findById("ray").orElseThrow();
        for (int i=0; i < 5; i++) {
            Address address = new Address();
            address.setId("test-"+i);
            address.setCountry("Indonesia");
            address.setStreet("Jalan");
            address.setCity("Legok");
            address.setPostalCode("74834");
            address.setContact(contact);
            address.setProvince("Banten");
            addressRepository.save(address);
        }

        mockMvc.perform(
                get("/api/contacts/ray/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "ray")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<AddressResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(5, response.getData().size());
        });
    }

    @Test
    void deleteAddressNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/contacts/ray/addresses/notfound")
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
    void deleteAddressSuccess() throws Exception {
        Contact contact = contactRepository.findById("ray").orElseThrow();
        Address address = new Address();
        address.setId("test");
        address.setCountry("Indonesia");
        address.setStreet("Jalan");
        address.setCity("Legok");
        address.setPostalCode("74834");
        address.setContact(contact);
        address.setProvince("Banten");
        addressRepository.save(address);

        mockMvc.perform(
                delete("/api/contacts/ray/addresses/test")
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

            assertFalse(addressRepository.existsById("test"));
        });
    }
}