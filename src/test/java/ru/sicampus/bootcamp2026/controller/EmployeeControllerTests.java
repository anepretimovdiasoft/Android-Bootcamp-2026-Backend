package ru.sicampus.bootcamp2026.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.sicampus.bootcamp2026.dto.EmployeeDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeEditDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeRegisterDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("tests")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void userLogin() throws Exception {
        this.mockMvc.perform(
                        post("/api/employee/login")
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isOk()
                );
    }

    @Test
    void userRegister() throws Exception {
        EmployeeRegisterDTO emp = new EmployeeRegisterDTO();
        emp.setName("Морозов Михаил Иванович");
        emp.setPosition("Тестировщик");
        emp.setUsername("test_mihail");
        emp.setEmail("tests@tests.ts");
        emp.setPhoneNumber("+79997778833");
        emp.setPassword("1234567812");

        this.mockMvc.perform(
                        post("/api/employee/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emp))
                )
                .andDo(print())
                .andExpect(status().isOk()
                );
    }

    @Test
    void userRegisterInvalidData() throws Exception {
        EmployeeRegisterDTO empNone = new EmployeeRegisterDTO();
        empNone.setName("");
        empNone.setPosition("");
        empNone.setUsername("");
        empNone.setEmail("");
        empNone.setPhoneNumber("");
        empNone.setPassword("");

        EmployeeRegisterDTO empInvalid = new EmployeeRegisterDTO();
        empInvalid.setName("Okak");
        empInvalid.setPosition("Okak");
        empInvalid.setUsername("aaa");
        empInvalid.setEmail("alhfhahjjemail.ru");
        empInvalid.setPhoneNumber("+67");
        empInvalid.setPassword("1234");

        EmployeeRegisterDTO empExists = new EmployeeRegisterDTO();
        empExists.setName("Иванов Иван Иванович");
        empExists.setPosition("DevOps");
        empExists.setUsername("iv_ivan");
        empExists.setEmail("ivan@indexzero.ru");
        empExists.setPhoneNumber("+79969121212");
        empExists.setPassword("1234561234");

        this.mockMvc.perform(
                        post("/api/employee/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(empNone))
                )
                .andDo(print())
                .andExpect(status().isBadRequest()
                );
        this.mockMvc.perform(
                        post("/api/employee/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(empInvalid))
                )
                .andDo(print())
                .andExpect(status().isBadRequest()
                );
        this.mockMvc.perform(
                        post("/api/employee/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(empExists))
                )
                .andDo(print())
                .andExpect(status().isConflict()
                );
    }

    @Test
    void userEdit() throws Exception {
        EmployeeEditDTO emp = new EmployeeEditDTO();
        emp.setName("Андрей Test");
        emp.setPosition("Разработчик Test");
        emp.setEmail("alimasov@test.ru");
        emp.setPhoneNumber("+79997778833");
        emp.setPhotoUrl("https://photo.com/image.jpg");

        MvcResult result = mockMvc.perform(
                        patch("/api/employee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emp))
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        EmployeeDTO employeeDTO = objectMapper.readValue(json, EmployeeDTO.class);

        assertEquals("Андрей Test", employeeDTO.getName());
        assertEquals("Разработчик Test", employeeDTO.getPosition());
        assertEquals("alimasov@test.ru", employeeDTO.getEmail());
        assertEquals("+79997778833", employeeDTO.getPhoneNumber());
        assertEquals("https://photo.com/image.jpg", employeeDTO.getPhotoUrl());
    }

    @Test
    void userEditInvalid() throws Exception {
        EmployeeEditDTO emp = new EmployeeEditDTO();
        emp.setName("Анд");
        emp.setEmail("alimasovtest.ru");
        emp.setPhoneNumber("+7999");
        emp.setPhotoUrl("http/photo.com");

        this.mockMvc.perform(
                        post("/api/employee/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emp))
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest()
                );
    }

    @Test
    void getUserByUsername() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/employee/indexzero")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        EmployeeDTO employeeDTO = objectMapper.readValue(json, EmployeeDTO.class);

        assertEquals("Калугин Олег Дмитриевич", employeeDTO.getName());
    }

    @Test
    void getUserByUsernameNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/api/employee/zzzzzzzzzzzzzzzzz")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

    }
}
