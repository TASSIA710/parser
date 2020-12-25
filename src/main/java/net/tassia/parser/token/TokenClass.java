package net.tassia.parser.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Links a class to a BNF rule.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenClass {

	/**
	 * The name of the BNF rule.
	 * @return the name
	 */
	String token();

}
