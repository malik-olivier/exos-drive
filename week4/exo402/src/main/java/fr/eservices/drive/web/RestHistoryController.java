package fr.eservices.drive.web;

import java.util.List;

import fr.eservices.drive.dao.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.eservices.drive.dao.StatusHistory;

// define as a REST Controller in spring context
@RestController
@RequestMapping(path = "/")
public class RestHistoryController {
	
	// Inject reference from spring context
	@Autowired
	HistorySource historySource;

	// map this opetation to GET only
	@GetMapping(path = "history/{orderId}")
	public List<StatusHistory> getHistory( @PathVariable int orderId ) {
		return historySource.orderHistory(orderId);
	}
	
	// map this operation to POST only
	@PostMapping(path = "history/{orderId}")
	public String addStatus( @PathVariable("orderId") int orderId, @RequestBody StatusHistory history ) {
		// try to add a status,
		// return "Ok" or "Error" if exception thrown
		try {
			historySource.addHistoryStatus(orderId,history);
			return "Ok";
		} catch (DataException e) {
			return "Error";
		}
	}
}
