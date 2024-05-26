package com.example.geocodingapi.controller;

import com.example.geocodingapi.service.MapService;
import com.example.geocodingapi.service.impl.MapServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "search", value = "/search")
public class SearchController extends HttpServlet {

    MapService mapService = new MapServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String queryParam = getQueryParam(request, response);

        try {
            String jsonResponse = mapService.searchLocationJsonAsStringByParam(queryParam);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to process request.");
        }
    }

    private String getQueryParam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String queryParam = request.getParameter("q");

        boolean isNotValid = queryParam == null || queryParam.trim().isEmpty();
        if (isNotValid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter 'q' is required.");
            throw new RuntimeException();
        }
        return queryParam;
    }

}
