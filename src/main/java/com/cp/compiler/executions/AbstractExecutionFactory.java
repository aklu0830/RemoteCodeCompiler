package com.cp.compiler.executions;

import org.springframework.web.multipart.MultipartFile;

/**
 * The interface Abstract execution factory.
 */
public interface AbstractExecutionFactory {
    
    /**
     * Create an instance of class Execution.
     *
     * @param sourceCode         the source code
     * @param inputFile          the input file
     * @param expectedOutputFile the expected output file
     * @param timeLimit          the time limit
     * @param memoryLimit        the memory limit
     * @return the execution
     */
    Execution createExecution(MultipartFile sourceCode,
                           MultipartFile inputFile,
                           MultipartFile expectedOutputFile,
                           int timeLimit,
                           int memoryLimit);
}
