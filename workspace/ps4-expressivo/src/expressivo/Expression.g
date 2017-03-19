/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

// grammar Expression;

/*
 *
 * You should make sure you have one rule that describes the entire input.
 * This is the "start rule". Below, "root" is the start rule.
 *
 * For more information, see the parsers reading.
 */
root ::= product | sum;
@skip whitespace{
	sum ::= primitive_1 ('+' primitive_1)*;
	product ::= primitive_2 ('*' primitive_2)*;
	token ::= number | variable;
	primitive_2 ::= token | '(' sum ')';
	primitive_1 ::= token | product | '(' product ')';
}
number ::= int | decimal;
int ::= [0-9]+;
decimal ::= [0-9]* '.' [0-9]+;
variable ::= [A-Za-z]+;
whitespace ::= [ ]+;
