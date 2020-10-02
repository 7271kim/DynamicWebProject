package hello;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class FilterChain extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURL().indexOf("ca_fr.css") > -1) {
            response.addHeader("Content-Disposition", "attachment");
        }
        filterChain.doFilter(request, response);
    }

}
