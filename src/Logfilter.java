import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.log4j.Logger;


public class Logfilter implements Filter {
    private ServletContext conteхt;
    private static Logger logger = Logger.getLogger(Logfilter.class);

    public Logfilter() {}

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chаin) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        Cookie[] cookies = httpReq.getCookies();

        String uri = httpReq.getRequestURI();
        String methodOrder = ((HttpServletRequest) request).getMethod();

        String filter = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("filter".equals(c.getName()))
                    filter = c.getValue();
            }
        }

        String user = (String)httpReq.getSession().getAttribute("username");

        //System.out.println("метод " + methodOrder + ", url " + uri + ", юзер " + user + ", фильтр " + filter);

        switch (uri){
            case "/":
                if(methodOrder.equals("POST")){
                    logger.info(uri+". пользователь " + httpReq.getParameter("j_username") + " вошёл, IP: " + httpReq.getRemoteAddr());
                }

                if(filter == "")
                    logger.info(uri+". Запрос всех товаров магазина.");
                else
                    logger.info(uri+". Запрос товаров типа " + filter);

                break;
            case "/order":
                if(methodOrder.equals("GET"))
                    logger.info("Пользователь "+ user + " решил оформить товар");
                else
                    logger.info("Пользователь " + user + " оформил товар");

                break;
            case "/item":
                int itemId = Integer.parseInt(request.getParameter("id"));
                logger.info("Просмотр товара с номером "+itemId);
                break;
            case "/cabinet":
                logger.info("Пользователь " + user +" зашёл в личный кабинет");
                break;

            case "/comment":
                String methodComment = ((HttpServletRequest) request).getMethod();
                if(methodComment.equals("POST"))
                    logger.info("Пользователь "+((HttpServletRequest) request).getUserPrincipal().getName()
                            +" оставил комментарий");
                else logger.info("Пользователь "+((HttpServletRequest) request).getUserPrincipal().getName()
                        +" запросил все комментарии");
                break;

            case "/exit":
                logger.info("Пользователь " + user + " вышел");

                break;
        }
        chаin.doFilter(request, response);
    }
    public void init(FilterConfig сonfig) throws ServletException
    {
        conteхt = сonfig.getServletContext();
    }
}
