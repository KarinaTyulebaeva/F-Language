# F-Language grammar



Program : Element { Element }  

Program : Element { Element } 

List : ( Element { Element } ) 

Element : Atom | Literal | List 

Atom : Identifier

Literal : [+|-] Integer | [+|-] Real | Boolean | null  

Identifier : Letter { Letter | DecimalDigit } 

Letter : Any Unicode character that represents a letter  

Integer : DecimalDigit { DecimalDigit }

DecimalDigit : 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 

Real : Integer . Integer 

Boolean : true | false