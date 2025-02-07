package com.ontariotechu.sofe3980U;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("10001"));
    }
	@Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    //Test 1: multiplication API
    @Test
    public void multiply() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "11").param("operand2", "10"))
            .andExpect(status().isOk())
            .andExpect(content().string("110"));
    }

    //Test 2: AND operation
    @Test
    public void bitwiseAnd() throws Exception {
        this.mvc.perform(get("/and").param("operand1", "1101").param("operand2", "1011"))
            .andExpect(status().isOk())
            .andExpect(content().string("1001"));
    }
    //Test 3: OR operation
    @Test
    public void bitwiseOr() throws Exception {
        this.mvc.perform(get("/or").param("operand1", "1101").param("operand2", "1011"))
            .andExpect(status().isOk())
            .andExpect(content().string("1111"));
    }
    // Edge Case: One operand is zero
    @Test
    public void multiplyWithZero() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "0").param("operand2", "10"))
            .andExpect(status().isOk())
            .andExpect(content().string("0"));
    }
    // Edge Case: Binary operands of different lengths
    @Test
    public void bitwiseAndWithDifferentLengthOperands() throws Exception {
        this.mvc.perform(get("/and").param("operand1", "1111").param("operand2", "11"))
            .andExpect(status().isOk())
            .andExpect(content().string("0011"));
    }
    // Edge Case: Both operands are zero (binary all zeros)
    @Test
    public void bitwiseOrWithZeros() throws Exception {
        this.mvc.perform(get("/or").param("operand1", "0000").param("operand2", "0000"))
            .andExpect(status().isOk())
            .andExpect(content().string("0000"));
    }
}
