package com.course.task.logic;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class Utility {
	
	private Pattern p = Pattern.compile("[a-zA-Z]+");
	
	public boolean checkName(String name) {
        return p.matcher(name).find();
    }
	
}