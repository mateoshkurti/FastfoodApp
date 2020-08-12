package ca.sheridancollege.shkurtim.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

	private Long menuID;
	private String menuName;
	private int price;
}
