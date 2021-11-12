package JavaClasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/removeContato")

public class RemoveContatoServlet extends HttpServlet {
    
    /**
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void service(HttpServletRequest request, 
            HttpServletResponse response)
            throws IOException, ServletException{
        
        
            PrintWriter out = response.getWriter();
            
            //pega o parâmetro fornecido
            String id = request.getParameter("id");
            
            //Convertendo uma string para um long
            long id_long = Long.parseLong(id.trim());
            
            Contato contato = new Contato();
                       
            //comando para setar a ID do contato
            contato.setId(id_long);
            
            ContatoDao dao = null;
                       
        try {
            dao = new ContatoDao();
            dao.remove(contato);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RemoveContatoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
    }
    
    
}
