package JavaClasses;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/contador")

public class ContadorServlet extends HttpServlet {
    private int contador = 0; //variável que irá contar
    
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        log("Iniciando a servlet");
    }
    
    
    @Override
    public void destroy(){
        super.destroy();
        log("Destruindo a servlet");
    }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        contador++; //a cada requisição o contador é incrementado
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>"
                + "<body>"
                + "Contador agora é: " + contador
                + "</body>"
                + "</html>");
    }
    
}
