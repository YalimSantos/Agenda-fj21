package JavaClasses;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ContatoDao {
    private final Connection connection;
    
    public ContatoDao() throws ClassNotFoundException {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Contato contato){
        String SQL_INSERT = "insert into contatos " + 
                "(nome, email, endereco, dataNascimento)" +
                " value (?, ?, ?, ?)";
        
        try{
            //seta os valores
            try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                                      Statement.RETURN_GENERATED_KEYS);) {
                //seta os valores
                statement.setString(1, contato.getNome());
                statement.setString(2, contato.getEmail());
                statement.setString(3, contato.getEndereco());
                statement.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
                
                statement.execute();
                                
                ResultSet generatedKeys;
                generatedKeys = statement.getGeneratedKeys();
                
                if (generatedKeys.next()) {
                    contato.setId(generatedKeys.getLong(1));
                }
                
                statement.close();
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Método para mostrar os valores
    public List<Contato> getLista(){
        try{
            List<Contato> contatos = new ArrayList<>();
            try (PreparedStatement stmt = this.connection.prepareStatement("select * from contatos"); 
            ResultSet rs = stmt.executeQuery()) {
                
                while(rs.next()){
                    //Criando o objeto contato
                    Contato contato = new Contato();
                    contato.setId(rs.getLong("id"));
                    contato.setNome(rs.getString("nome"));
                    contato.setEmail(rs.getString("email"));
                    contato.setEndereco(rs.getString("endereco"));
                    
                    //Usando o calendar pra mostrar a data
                    Calendar data = Calendar.getInstance();
                    data.setTime(rs.getDate("dataNascimento"));
                    contato.setDataNascimento(data);
                    
                    //Adicionando contato à lista
                    contatos.add(contato);
                }
                
                rs.close();
                stmt.close();
            }
            return contatos;
            
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void altera(Contato contato) {
	String sql = "update contatos set nome=?, email=?,"+
                "endereco=?, dataNascimento=? where id=?";
        
	try {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, contato.getNome());
                stmt.setString(2, contato.getEmail());
                stmt.setString(3, contato.getEndereco());
                stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
                stmt.setLong(5, contato.getId());
                
                stmt.execute();
                stmt.close();
            }
	}catch (SQLException e){
            throw new RuntimeException(e);
	}
    }

    public void remove(Contato contato){
        try {
            try (PreparedStatement stmt = connection
                        .prepareStatement("delete from contatos where id=?")) {
                stmt.setLong(1, contato.getId());
                stmt.execute();
                stmt.close();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
