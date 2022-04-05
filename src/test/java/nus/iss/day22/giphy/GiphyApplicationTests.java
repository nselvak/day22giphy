package nus.iss.day22.giphy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nus.iss.day22.giphy.service.SearchService;

@SpringBootTest
class GiphyApplicationTests {

	@Autowired
	private SearchService svc;

	
	String query="pokemon";
	String rating="pg";
	Integer limit=5;


	// This is for checking if the model is returned properly
	//@Test
	//void shouldReturnAGame() {
	//	Optional<Game> opt = gameRepo.getGameByGid(10);
	//	assertTrue(opt.isPresent(), "gid = 10");
	//}

	// return a non empty list
	@Test
	void shouldReturnList(){
		List<String> opt = svc.searchGiphy(query, limit, rating);
		assertFalse(opt.isEmpty());
	}


	// return same length as limit
	@Test
	void shouldReturnAccurateLimit(){
		List<String> opt = svc.searchGiphy(query, limit, rating);
		assertEquals(opt.size(), limit);
	}

	// return default value 
	@Test
	void shouldReturn10Limit(){
		List<String> opt = svc.searchGiphy(query);
		assertEquals(10, opt.size(), "Default number");
	}

	// return a non empty list for overloaded method 1
	@Test
	void shouldReturnList1(){
		List<String> opt = svc.searchGiphy(query);
		assertFalse(opt.isEmpty());
	}

	// return a non empty list for overloaded method 2
	@Test
	void shouldReturnList2(){
		List<String> opt = svc.searchGiphy(query, limit);
		assertFalse(opt.isEmpty());
	}

	// return a non empty list for overloaded method 3
	@Test
	void shouldReturnList3(){
		List<String> opt = svc.searchGiphy(query, rating);
		assertFalse(opt.isEmpty());
	}

}
