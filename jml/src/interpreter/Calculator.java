package interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import symbolTable.VarType;
import symbolTable.Variable;
import CommonClasses.*;
import CommonClasses.Error;

public class Calculator {

	
	static Variable solve(List<SintaxElement> se) throws Error {
		// Esta lista cont�m apenas CONST e OP
		Variable result = new Variable("");
		
		SintaxElement first;
		SintaxElement second;
		// Associativa a esquerda para facilitar minha vida
		// Tem dois ou mais elementos
		while (se.size()!=1) {
			first = se.remove(0); // Pegar os dois primeiros para avaliar
			second = se.remove(1);
			// Operadores un�rios
			if (first.getId()==SintaxElementId.OP && first.getLexem().isUnaryOperator()) {
				Lexem lex = first.getLexem();
				Variable v = new Variable("");
				if (lex.getLex().compareTo("~-")==0) { // Inteiro
					if (typeFromLex(second.getLexem())==VarType.INT_TYPE) {
						v = variableFromElement(second);
						Integer value = (Integer) v.getValue();
						v.setValue(value * -1);
					}
				}
				else if (lex.getLex().compareTo("~-.")==0) { // Float
					if (typeFromLex(second.getLexem())==VarType.FLOAT_TYPE) {
						v = variableFromElement(second);
						Float value = (Float) v.getValue();
						v.setValue(value * -1);
					}					
				}
				else if (lex.getLex().compareTo("!")==0) { // Bool
					if (typeFromLex(second.getLexem())==VarType.BOOL_TYPE) {
						v = variableFromElement(second);
						Boolean value = (Boolean) v.getValue();
						v.setValue(!value);
					}
				}
				// Removes os dois valores, e coloca o novo elemento
				Lexem newLex = new Lexem((String) v.getValue());
				newLex.evalue();
				SintaxElement newElement = new SintaxElement(newLex);
				se.add(0, newElement);
			}
			// Operadores bin�rios
			else { // Tratar todas as outras opera��es aki
				
			}
		}
		result = variableFromElement(se.get(0));
		
		return result;
	}
	

	private static Variable variableFromElement(SintaxElement sintaxElement) {
		// TODO Auto-generated method stub
		return null;
	}


	private static VarType typeFromLex(Lexem l) {
		switch (l.getId()) {
			case TYPE_BOOL: return VarType.BOOL_TYPE;
			case TYPE_FLOAT: return VarType.FLOAT_TYPE;
			case TYPE_CHAR: return VarType.CHAR_TYPE;
			case TYPE_INT: return VarType.INT_TYPE;
			case TYPE_LIST: return VarType.LIST_TYPE;
			case TYPE_STRING: return VarType.STRING_TYPE;
		}
		return null;
	}


	private static VarType operatorType(SintaxElement e) throws Error {
		Lexem l = e.getLexem();
		switch (l.getId()) {
			case OPERATOR_AND: return VarType.BOOL_TYPE;
			case OPERATOR_EQUAL: return VarType.BOOL_TYPE;
			case OPERATOR_GREATEREQUALTHAN: return VarType.BOOL_TYPE;
			case OPERATOR_GREATERTHAN: return VarType.BOOL_TYPE;
			case OPERATOR_LESSEQUALTHAN: return VarType.BOOL_TYPE;
			case OPERATOR_LESSTHAN: return VarType.BOOL_TYPE;
			case OPERATOR_NONEQUAL: return VarType.BOOL_TYPE;
			case OPERATOR_NOT: return VarType.BOOL_TYPE;
			case OPERATOR_OR: return VarType.BOOL_TYPE;
			case OPERATOR_POW: return VarType.BOOL_TYPE;
			case OPERATOR_LISTAPPEND: return VarType.LIST_TYPE;
			case OPERATOR_LISTCONCAT: return VarType.LIST_TYPE;
			case OPERATOR_MOD: return VarType.INT_TYPE;
			case OPERATOR_STRCONCAT: return VarType.STRING_TYPE;
			case OPERATOR_MUL:    
			case OPERATOR_DIV: 
			case OPERATOR_SUB:
			case OPERATOR_SUM:
			case OPERATOR_UNARYMINUS: return (l.getLex().endsWith(".")? VarType.FLOAT_TYPE : VarType.INT_TYPE);
			default : return VarType.UNKNOWN;
		}
	}
}