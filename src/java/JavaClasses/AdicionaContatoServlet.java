package JavaClasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/adicionaContato")

public class AdicionaContatoServlet extends HttpServlet {

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
            throws IOException, ServletException {
        
        // busca o writer
        PrintWriter out = response.getWriter();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        //buscando os parâmetros no request
        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");
        String email = request.getParameter("email");
        String dataEmTexto = request.getParameter("dataNascimento");
        Calendar dataNascimento = null;
        
        // fazendo a conversão da data
        
        try{
            Date date = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(dataEmTexto);
            dataNascimento = Calendar.getInstance();
            dataNascimento.setTime(date);
        } catch (ParseException e) {
            out.write("Erro de conversão da data");
            return; //para a execução do método
        }
        
        //monta um objeto contato
        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setEndereco(endereco);
        contato.setEmail(email);
        contato.setDataNascimento(dataNascimento);
        
        // salva o contato
        ContatoDao dao = null;
        try {
            dao = new ContatoDao();
            dao.adiciona(contato);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdicionaContatoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_novo", contato.getId());
        } catch (JSONException ex) {
            Logger.getLogger(AdicionaContatoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.write(obj.toString());
        
        
        //imprime o nome do contato que foi adicionado
        
        /*out.write("<html>"
                + "<body>"
                + "Contato "+ 
                " adicionado com sucesso"
                + "</body>"
                + "</html>");*/
        
    }
}
