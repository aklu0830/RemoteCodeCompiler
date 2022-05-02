package com.cp.compiler.controllers;

import com.cp.compiler.executions.Execution;
import com.cp.compiler.executions.ExecutionFactory;
import com.cp.compiler.models.*;
import com.cp.compiler.services.CompilerFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Compiler Controller Class, this class exposes 4 endpoints for (Java, C, CPP, and Python)
 *
 * @author Zakaria Maaraki
 */
@RestController
@RequestMapping("/api")
public class CompilerController {
    
    private CompilerFacade compiler;
    
    /**
     * Instantiates a new Compiler controller.
     *
     * @param compiler the compiler
     */
    public CompilerController(CompilerFacade compiler) {
        this.compiler = compiler;
    }
    
    /**
     * Take as a parameter a json object
     *
     * @param request    object
     * @param preferPush the prefer push
     * @param url        the url
     * @return The verdict of the execution (Accepted, Wrong Answer, Time Limit Exceeded, Memory Limit Exceeded, Compilation Error, RunTime Error)
     * @throws Exception the exception
     */
    @PostMapping("/compile/json")
    @ApiOperation(
            value = "Json",
            notes = "You should provide outputFile, inputFile (not required), source code, time limit and memory limit",
            response = Response.class
    )
    public ResponseEntity<Object> compile(@ApiParam(value = "request") @RequestBody Request request,
                                          @RequestHeader(value = WellKnownParams.PREFER_PUSH, required = false) String preferPush,
                                          @RequestHeader(value = WellKnownParams.URL, required = false) String url)
            throws Exception {
        
        Execution execution = ExecutionFactory.createExecution(
                request.getSourceCode(),
                request.getInput(),
                request.getExpectedOutput(),
                request.getTimeLimit(),
                request.getMemoryLimit(),
                request.getLanguage());
        
        boolean isLongRunning = WellKnownHeaders.PREFER_PUSH.equals(preferPush);
        return compiler.compile(execution, isLongRunning, url);
    }
    
    /**
     * Compiler Controller
     *
     * @param language    the programming language
     * @param outputFile  Expected output
     * @param sourceCode  Python source code
     * @param inputFile   Input data (optional)
     * @param timeLimit   Time limit of the execution, must be between 0 and 15 sec
     * @param memoryLimit Memory limit of the execution, must be between 0 and 1000 MB
     * @param preferPush  the prefer push
     * @param url         the url
     * @return The verdict of the execution (Accepted, Wrong Answer, Time Limit Exceeded, Memory Limit Exceeded, Compilation Error, RunTime Error)
     * @throws Exception the exception
     */
    @PostMapping("/compile")
    @ApiOperation(
            value = "Multipart request",
            notes = "You should provide outputFile, inputFile (not required), source code, time limit and memory limit "
                    + "and the language",
            response = Response.class
    )
    public ResponseEntity compile(
            @ApiParam(value = "The language")
            @RequestParam(value = WellKnownParams.LANGUAGE) Language language,
            
            @ApiParam(value = "The expected output")
            @RequestPart(value = WellKnownParams.OUTPUT_FILE) MultipartFile outputFile,
            
            @ApiParam(value = "Your source code")
            @RequestPart(value = WellKnownParams.SOURCE_CODE) MultipartFile sourceCode,
            
            @ApiParam(value = "This one is not required, it's just the inputs")
            @RequestParam(value = WellKnownParams.INPUT_FILE, required = false) MultipartFile inputFile,
            
            @ApiParam(value = "The time limit that the execution must not exceed")
            @RequestParam(value = WellKnownParams.TIME_LIMIT) int timeLimit,
            
            @ApiParam(value = "The memory limit that the running program must not exceed")
            @RequestParam(value = WellKnownParams.MEMORY_LIMIT) int memoryLimit,

            @RequestHeader(value = WellKnownParams.PREFER_PUSH, required = false) String preferPush,
            @RequestHeader(value = WellKnownParams.URL, required = false) String url) throws Exception {
        
        Execution execution = ExecutionFactory.createExecution(
                sourceCode, inputFile, outputFile, timeLimit, memoryLimit, language);
        
        boolean isLongRunning = WellKnownHeaders.PREFER_PUSH.equals(preferPush);
        return compiler.compile(execution, isLongRunning, url);
    }
}
