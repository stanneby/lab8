package servlet;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.ChatMessage;
import entity.ChatUser;


@WebServlet(name = "NewMessageServlet")
public class NewMessageServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;
    private static final String wordsToCensor = "mat, nepotrebshina, Gendo Ikari";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // По умолчанию используется кодировка ISO-8859. Так как мы
        // передаѐмданныевкодировкеUTF-8//
        // то необходимо установить соответствующую кодировкуHTTP-запроса
        request.setCharacterEncoding("UTF-8");
        // ИзвлечьизHTTP-запросапараметр'message'
        String message = (String)request.getParameter("message");
        // Если сообщение не пустое, то

        if(message!=null&& !"".equals(message)) {
            String[] arrayOfWordsToCensor = wordsToCensor.split(",\\s");
            for(int i = 0; i < arrayOfWordsToCensor.length; i++){
                message = message.replaceAll(arrayOfWordsToCensor[i], "*бип*");
            }
            // По имени из сессии получить ссылку на объект ChatUse
            ChatUser author = activeUsers.get((String) request.getSession().getAttribute("name"));


            synchronized(messages) {
                // Добавить всписок сообщений новое
                messages.add(new ChatMessage(message, author, Calendar.getInstance().getTimeInMillis()));
            }

        }
        // Перенаправитьпользователянастраницусформойсообщения
        response.sendRedirect("/Laba8/compose_message.jsp");
    }
}

