package com.example.geocodingapi.controller;

import com.example.geocodingapi.service.MapService;
import com.example.geocodingapi.service.impl.MapServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "reverse", value = "/reverse")
public class ReverseController extends HttpServlet {

    private final MapService mapService = new MapServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String latParam = request.getParameter("lat");
        String lonParam = request.getParameter("lon");

        if ((latParam == null || latParam.trim().isEmpty()) && (lonParam == null || lonParam.trim().isEmpty())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter 'lat' or 'lon' is required.");
            return;
        }

        String jsonResponse = mapService.reverseLocationJsonAsStringByParams(lonParam, latParam);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

}
