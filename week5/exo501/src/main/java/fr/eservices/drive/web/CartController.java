package fr.eservices.drive.web;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eservices.drive.dao.ArticleDao;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Cart;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.eservices.drive.dao.CartDao;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.dao.OrderDao;
import fr.eservices.drive.web.dto.CartEntry;
import fr.eservices.drive.web.dto.SimpleResponse;
import fr.eservices.drive.web.dto.SimpleResponse.Status;

@Controller
@RequestMapping(path="/cart")
public class CartController {
	
	@Autowired
	CartDao daoCart;

	@Autowired
    ArticleDao articleDao;

	@Autowired
    OrderRepository orderRepository;
	
	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String dataExceptionHandler(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter w = new PrintWriter( out );
		ex.printStackTrace(w);
		w.close();
		return 
			"ERROR"
			+ "<!--\n" + out.toString() + "\n-->";
	}
	
	@GetMapping(path="/{id}.html", produces="text/html")
	public String getCart(@PathVariable(name="id") int id, Model model) throws DataException {
		if(id <= 0) throw new DataException("id isn't valid");
		// get cart from dao
		Cart cart = daoCart.getCartContent(id);
		if(cart ==null) cart = new Cart();
		// assign to model var "cart"
		model.addAttribute("cart",cart);
		// return view name to display content of /WEB-INF/views/_cart_header.jsp
		return "_cart_header";
	}

	@ResponseBody
	@PostMapping(path="/{id}/add.json",consumes="application/json")
	public SimpleResponse add(@PathVariable(name="id") int id, @RequestBody CartEntry art) throws DataException {
		SimpleResponse res = new SimpleResponse();
		

		System.out.println(
			"********************\n"
			+ "***** " + String.format("Add Article %d x [%s] to cart", art.getQty(), art.getId()) + "\n" 
			+ "********************"
		);

		Article article = articleDao.find(art.getId());
        res.status = ((article != null && art.getQty() > 0) ? Status.OK : Status.ERROR);
        if(res.status.equals(Status.OK)) {
        	Cart cart = daoCart.getCartContent(id);
        	if(cart == null) {
        		cart = new Cart();
        		daoCart.store(id,cart);
			}
        	cart.getArticles().add(article);
		}
		return res;
	}
	
	@RequestMapping("/{id}/validate.html")
	public String validateCart(@PathVariable(name="id") int id, Model model) throws DataException {
		
		// get cart by its id
        Cart cart = daoCart.getCartContent(id);
        if(cart == null) throw new DataException("cart no found");
		// create an order
        Order order = new Order();
        order.setCustomerId("chuckNorris");
        order.setCurrentStatus(fr.eservices.drive.dao.Status.ORDERED);
		// for each article, add it to the order
        int amount = 0;
        for (Article article: cart.getArticles()) {
            order.getArticles().add(article.getId());
            amount += article.getPrice();
        }
        order.setArticles(order.getArticles());
		// set order date
        order.setCreatedOn(new Date());
		// set order amount (sum of each articles' price)
        order.setAmount(amount);
		// persist everything
        orderRepository.save(order);
		// redirect user to list of orders
		List<Order> orders = new ArrayList<>();
		orders = orderRepository.findByCustomerIdOrderByCreatedOnDesc("chuckNorris");
		model.addAttribute("orders",orders);
		return "redirect: /exo501/order/ofCustomer/chuckNorris.html";
	}
}
