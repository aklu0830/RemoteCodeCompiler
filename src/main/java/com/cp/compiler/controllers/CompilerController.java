package com.cp.compiler.controllers;

import com.cp.compiler.exceptions.CompilerServerException;
import com.cp.compiler.executions.Execution;
import com.cp.compiler.executions.ExecutionFactory;
import com.cp.compiler.models.Language;
import com.cp.compiler.models.Request;
import com.cp.compiler.models.Response;
import com.cp.compiler.services.CompilerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Compiler Controller Class, this class exposes 4 endpoints for (Java, C, CPP, and Python)
 *
 * @author Zakaria Maaraki
 */

@RestController
@RequestMapping("/compiler")
public class CompilerController {
    
    private CompilerService compiler;
    
    public CompilerController(@Qualifier("proxy") CompilerService compiler) {
        this.compiler = compiler;
    }
    
    /**
     * Take as a parameter a json object
     *
     * @param request object
     * @return The verdict of the execution (Accepted, Wrong Answer, Time Limit Exceeded, Memory Limit Exceeded, Compilation Error, RunTime Error)
     * @throws CompilerServerException The compiler exception
     */
    @PostMapping("/json")
    @ApiOperation(
            value = "json",
            notes = "Provide outputFile, inputFile (not required), source code, time limit and memory limit",
            response = Response.class
    )
    public ResponseEntity<Object> compile(@ApiParam(value = "request") @RequestBody Request request)
            throws Exception {
        return compiler.compile(request);
    }
    
    /**
     * Python Compiler Controller
     *
     * @param outputFile  Expected output
     * @param sourceCode  Python source code
     * @param inputFile   Input data (optional)
     * @param timeLimit   Time limit of the execution, must be between 0 and 15 sec
     * @param memoryLimit Memory limit of the execution, must be between 0 and 1000 MB
     * @return The verdict of the execution (Accepted, Wrong Answer, Time Limit Exceeded, Memory Limit Exceeded, Compilation Error, RunTime Error)
     * @throws CompilerServerException The compiler exception
     */
    @PostMapping("/python")
    @ApiOperation(
            value = "Python compiler",
            notes = "Provide outputFile, inputFile (not required), source code, time limit and memory limit",
            response = Response.class
    )
    public ResponseEntity<Object> compilePython(
            @ApiParam(value = "The expected output")
            @RequestPart(value = "outputFile") MultipartFile outputFile,
            
            @ApiParam(value = "Your source code")
            @RequestPart(value = "sourceCode") MultipartFile sourceCode,
            
            @ApiParam(value = "This one is not required, it's just the inputs")
            @RequestParam(value = "inputFile", required = false) MultipartFile inputFile,
            
            @ApiParam(value = "The time limit that the execution must not exceed")
            @RequestParam(value = "timeLimit") int timeLimit,
            
            @ApiParam(value = "The memory limit that the running program must not exceed")
            @RequestParam(value = "memoryLimit") int memoryLimit
    ) throws Exception {
        Execution execution = ExecutionFactory.createExecution(
                sourceCode, inputFile, outputFile, timeLimit, memoryLimit, Language.PYTHON);
        return compiler.compile(execution);
    }
    
    /**
     * C Compiler Controller
     *
     * @param outputFile  Expected output
     * @param sourceCode  C source code
     * @param inputFile   Input data (optional)
     * @param timeLimit   Time limit of the execution, must be between 0 and 15 sec
     * @param memoryLimit Memory limit of the execution, must be between 0 and 1000 MB
     * @return The verdict of the execution (Accepted, Wrong Answer, Time Limit Exceeded, Memory Limit Exceeded,
     * Compilation Error, RunTime Error)
     * @throws CompilerServerException the compiler exception
     */
    @PostMapping("/c")
    @ApiOperation(
            value = "C compiler",
            notes = "Provide outputFile, inputFile (not required), source code, time limit and memory limit",
            response = Response.class
    )
    public ResponseEntity<Object> compileC(
            @ApiParam(value = "The expected output")
            @RequestPart(value = "outputFile") MultipartFile outputFile,
            
            @ApiParam(value = "Your source code")
            @RequestPart(value = "sourceCode") MultipartFile sourceCode,
            
            @ApiParam(value = "This one is not required, it's just the inputs")
            @RequestParam(value = "inputFile", required = false) MultipartFile inputFile,
            
            @ApiParam(value = "The time limit that the execution must not exceed")
            @RequestParam(value = "timeLimit") int timeLimit,
            
            @ApiParam(value = "The memory limit that the running program must not exceed")
            @RequestParam(value = "memoryLimit") int memoryLimit
    ) throws Exception {
        Execution execution = ExecutionFactory.createExecution(
                sourceCode, inputFile, outputFile, timeLimit, memoryLimit, Language.C);
        return compiler.compile(execution);
    }
    
    /**
     * C++ Compiler Controller
     *
     * @param outputFile  Expected output
     * @param sourceCode  C++ source code
     * @param inputFile   Input data (optional)
     * @param timeLimit   Time limit of the execution, must be between 0 and 15 sec
     * @param memoryLimit Memory limit of the execution, must be between 0 and 1000 MB
     * @return The verdict of the execution (Accepted, Wrong Answer, Time Limit Exceeded, Memory Limit Exceeded,
     * Compilation Error, RunTime Error)
     * @throws CompilerServerException the compiler exception
     */
    @PostMapping("/cpp")
    @ApiOperation(
            value = "Cpp compiler",
            notes = "Provide outputFile, inputFile (not required), source code, time limit and memory limit",
            response = Response.class
    )
    public ResponseEntity<Object> compileCpp(
            @ApiParam(value = "The expected output")
            @RequestPart(value = "outputFile") MultipartFile outputFile,
            
            @ApiParam(value = "Your source code")
            @RequestPart(value = "sourceCode") MultipartFile sourceCode,
            
            @ApiParam(value = "This one is not required, it's just the inputs")
            @RequestParam(value = "inputFile", required = false) MultipartFile inputFile,
            
            @ApiParam(value = "The time limit that the execution must not exceed")
            @RequestParam(value = "timeLimit") int timeLimit,
            
            @ApiParam(value = "The memory limit that the running program must not exceed")
            @RequestParam(value = "memoryLimit") int memoryLimit
    ) throws Exception {
        Execution execution = ExecutionFactory.createExecution(
                sourceCode, inputFile, outputFile, timeLimit, memoryLimit, Language.CPP);
        return compiler.compile(execution);
    }
    
    /**
     * Java Compiler Controller
     *
     * @param outputFile  Expected output
     * @param sourceCode  Java source code
     * @param inputFile   Input data (optional)
     * @param timeLimit   Time limit of the execution, must be between 0 and 15 sec
     * @param memoryLimit Memory limit of the execution, must be between 0 and 1000 MB
     * @return The verdict of the execution (Accepted, Wrong Answer, Time Limit Exceeded, Memory Limit Exceeded,
     * Compilation Error, RunTime Error)
     * @throws CompilerServerException the compiler exception
     */
    @PostMapping("/java")
    @ApiOperation(
            value = "Java compiler",
            notes = "Provide outputFile, inputFile (not required), source code, time limit and memory limit",
            response = Response.class
    )
    public ResponseEntity<Object> compileJava(
            @ApiParam(value = "The expected output")
            @RequestPart(value = "outputFile") MultipartFile outputFile,
            
            @ApiParam(value = "Your source code")
            @RequestPart(value = "sourceCode") MultipartFile sourceCode,
            
            @ApiParam(value = "This one is not required, it's just the inputs")
            @RequestParam(value = "inputFile", required = false) MultipartFile inputFile,
            
            @ApiParam(value = "The time limit that the execution must not exceed")
            @RequestParam(value = "timeLimit") int timeLimit,
            
            @ApiParam(value = "The memory limit that the running program must not exceed")
            @RequestParam(value = "memoryLimit") int memoryLimit
    ) throws Exception {
        Execution execution = ExecutionFactory.createExecution(
                sourceCode, inputFile, outputFile, timeLimit, memoryLimit, Language.JAVA);
        return compiler.compile(execution);
    }
    
}
