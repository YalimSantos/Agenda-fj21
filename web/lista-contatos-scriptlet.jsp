<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "JavaClasses.*" %>
<%@ page import ="java.util.*"%>
<%@ page import = "java.text.SimpleDateFormat"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Tabela dos contatos</title>
            
            <style type="text/css">
                table{
                    border-collapse: collapse;
		}
                td, th{
                    border: 1px solid #000000;
                    width: 200px;
                    text-align: center;
                }
                p{
                    text-align: center;
                }
                #adicionaDiv{
                    padding: 10px;
                    border: 2px solid #000000;
                    width: 350px;
                }
                .selecionado{
                    background-color: #d1ccc0;
                }
                .escondeBotao{
                    float: right;
                    top: 5px;
                    right: 2px;
                    background-color: #ff6b6b;
                }
                #adicionaDiv{
                    display: none;
                }
            </style>
            
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            
            <script>     
                
                function onSelectColumn( el ){	                  
                        var td = el.target;                                      
                        
                        $(td).parent().addClass("selecionado");
                        $(td).parent().siblings().removeClass("selecionado");	
                    }
                
                $(document).ready(function(){                            
                    $("#adicionaBotao").click(function(){
                        $("#adicionaDiv").show(); 
                    });

                    $("#adicionaContato").click(function(){
                        $("#adicionaDiv").hide();
                        
                        var nome = $("#nome").val();
                        var email = $("#email").val();
                        var endereco = $("#endereco").val();
                        var dataNascimento = $("#dataNascimento").val();
                                             
                        $.ajax({
                            url: "./adicionaContato",
                            method: "POST",
                            data: { nome, email, endereco, dataNascimento },
                            success:function( data ){
                                var conteudo = "";
                                
                                conteudo = "<tr> <input type='hidden' value='" + data.id_novo + "'>" +
                                        "<td onclick='onSelectColumn(event)'> " + nome + " </td>" +
                                        "<td onclick='onSelectColumn(event)'> " + email + " </td>" +
                                        "<td onclick='onSelectColumn(event)'> " + endereco + " </td>" +
                                        "<td onclick='onSelectColumn(event)'> " + dataNascimento + " </td> </tr>";
                                
                               $("table").append(conteudo); 
                               alert("Contato adicionado com sucesso!");
                            },
                            error:function(){
                                alert("error");
                            }
                        });
                       /*$.post("./adicionaContato",
                        {
                            nome: nome,
                            email: email,
                            endereco: endereco,
                            dataNascimento: dataNascimento
                            
                        },
                        function(data, status){
                            alert(status);
                        });*/
                    });

                    $(".escondeBotao").click(function(){
                        $("#adicionaDiv").hide();
                    });
                    
                    $("td").click(onSelectColumn);
                    
                    $("#excluiBotao").click(function(){
                        var id = $(".selecionado").find("input").val();
                        
                        if(id !== ""){                                                   
                            $.ajax({
                                url: "./removeContato",
                                method: "POST",
                                data: {id},
                                success:function(){
                                    $(".selecionado").remove();
                                    alert("Contato removido com sucesso");
                                },
                                error:function(){
                                    alert("error");
                                }
                            });
                        }
                        else{
                            alert("Nenhum contato selecionado");
                        }
                    });
                });
                
            </script>
            
        </head>
        
        
        <body>
            
            <div>
                <p>Bem vindo ao sistema de agenda<br>
                   Desenvolvido por Yalim</p>
                <hr><br>
            </div>
            
            <table>
		<th>Nome</th>
                <th>Email</th>
		<th>Endereço</th>
                <th>Data de nascimento</th>
		
                <%
                    ContatoDao dao = new ContatoDao();
                    
                    List<Contato> contatos = dao.getLista();
                    
                    for (Contato contato : contatos) {
                %>
                        
                <tr>
                    <input type="hidden" value="<%=contato.getId() %>">
                    <td> <%=contato.getNome() %> </td>
                    <td> <%=contato.getEmail() %> </td>
                    <td> <%=contato.getEndereco() %> </td>
                    
                    <%
                        Date date;
                        date = contato.getDataNascimento().getTime();
                        SimpleDateFormat formatter;
                        formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = formatter.format(date);
                    %>
                    
                    <td> <%=strDate %> </td>
                    
                </tr>
               
                <%
                    }
                %>
                
            </table>
            
            <br>
            <button id="adicionaBotao">Adicionar contato</button>
            <button id="excluiBotao">Excluir contato selecionado</button>
            <br><br>
                
            <div id="adicionaDiv">
                <button class="escondeBotao">X</button>
                <h1>Adiciona Contatos</h1>
        
                <hr />
        
                <form>
                    Nome: <input type="text" id="nome"/> <br/>
                    E-mail: <input type="text" id="email"/> <br/>
                    Endereço: <input type="text" id="endereco"/> <br/>
                    Data Nascimento: <input type="text" id="dataNascimento"/> <br/>
                    <input type="button" id="adicionaContato" value="Adicionar contato"/>
                    <input type="reset" value="Apagar">
                </form>
            </div>
        </body>
    </html>
</f:view>
