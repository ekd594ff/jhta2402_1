package com.desk8432.project.util;

import com.desk8432.project.dto.TestDTO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

public class Dispatcher {
    private static final long serialVersionUID = 1L;

    protected void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String jsonString = getBody(req);
        Gson gson = new Gson();
        TestDTO testDTO = gson.fromJson(jsonString, TestDTO.class);
        System.out.println(testDTO.getData());
    }

    public static String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        body = stringBuilder.toString();
        return body;
    }
}