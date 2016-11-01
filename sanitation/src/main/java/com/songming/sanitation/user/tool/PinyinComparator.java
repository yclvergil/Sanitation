package com.songming.sanitation.user.tool;

import java.util.Comparator;

import com.songming.sanitation.user.model.UserDto;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<UserDto> {

	public int compare(UserDto o1, UserDto o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
