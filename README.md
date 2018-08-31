# reverse-hangman



Wikipedia defines hangman game as follows:
Hangman is a paper and pencil guessing game for two or more players. One player thinks of a word, phrase or sentence and the other tries to guess it by suggesting letters or numbers.
The word to guess is represented by a row of dashes, representing each letter of the word. Words you cannot use include proper nouns such as names, places, and brands. If the guessing player suggests a letter which occurs in the word, the other player writes it in all its correct positions. If the suggested letter or number does not occur in the word, the other player draws one element of a hanged man stick figure as a tally mark. The game is over when:

An example game in progress; the answer is Wikipedia.
	•	The guessing player completes the word, or guesses the whole word correctly
	•	The other player completes the diagram:
This diagram is, in fact, designed to look like a hanging man. Although debates have arisen about the questionable taste of this picture, it is still in use today. 

As the definition of the game indicates, this game is mainly played between two people. There are some games available online (www.playhangman.com) where computer picks a word (sentences) and user is trying to find the word (sentences). In this project, you will do the opposite, that is, you will develop a data structure, which will be storing given words from English Language, and code such that user guess a word from the given data and your code is supposed to find the word with minimum number of characters entered by user.


There may be different user interface for this game if the game is written in GUI(www.playhangman.com, https://www.proprofs.com/games/word-games/hangman/, http://www.webhangman.com/hangman.php). 
For this project, I am expecting that most group will not use GUI. So if you implement an algorithm that will be taking input through keyboard, then how can we read the input and decide if such character is in the given word or not. For this, there may be several ways to do also: Here is one that I can suggest.
For example: User has chosen the word ABLE (taken from the given data). User wants your code to determine this word. Initially, your code is going to ask the length of the word. User enters 4. Since there are many words with length four, it is impossible to guess the correct one. So code is going to ask for character. Let us assume that code asked for C. User need to indicate that C is not in the word. How can you do that? One way would be: if asked character is in the word, one can give the index position which is positive numbers. For the C, we would give -1 indication that C does not exist in the word. Then code should ask for new character. Probably here you do not want to choose random character (You may want to choose a character that appears most in a typical English word). If the code asks for A, then user enters 1. Is it possible to determine? No because there are more than one word in the data base with four character and starts with A. So code will continue to ask. Next character that is asked for is E. Here you should be able to determine the word because there is only one word with length four and starting with A and ending with E, that is, ABLE in the given data. Again it is totally up to you how to ‘tell” to the code whether a given character is in the word. Example that I gave is one way. In this way, you may need to enter more than one number to indicate the position(s) of the character.
 Here how your program is supposed to do:
	•	Ask for the length of the word that user has picked up
	•	With a certain strategy, ask user for a character
	•	If you have missed 9 characters before word is determined, then you will lose the game. So your objective is to determine the word without missing 9 characters.
                  
	•	Main objective of you code is to determine the word with minimum possible characters. This depends on the search and data structure that you will be using.

Objective of this project is search and data structure that you will choose. Your code is basically search for the word that is picked from the database.  At the beginning, you only know the length of the word. That should restrict the search to certain part of the data base. Each time, when code gets a new character, it should help to put more restriction on the reach.  If you ask for all 26 characters from the English alphabet which you cannot because 26>9), then search is meaningless. Your objective is to design a database and search algorithm such that, it will find the given word with minimum number for characters in most efficient time. I will provide the database to choose words from. 


