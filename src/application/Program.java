package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		//Criar partida
		ChessMatch chessMatch = new ChessMatch();
		
		//Definir lista de peças
		List<ChessPiece> captured = new ArrayList<ChessPiece>();
		
		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				//imprimir o tanuleiro no ecra
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				//Colorir as posições possiveis para onde a minha peça pode mover
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				//Mandar executar o movimento de xadrez e retornar a uma possivel peça capturada
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				//Se a peça capturada for diferente de nulo siginfica que alguma peça foi capturada
				if (capturedPiece != null) {
					//Adicionar na lista de peças capturadas
					captured.add(capturedPiece);
				}
				
				//Significa que a peça foi promovida
				if (chessMatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine().toUpperCase();
					
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q") ) {
						System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
						type = sc.nextLine().toUpperCase();
					}
					//Troca a peça que foi promovida
					chessMatch.replacePromotedPiece(type);
				}
				
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		//Imprimir a partida finalizada
		UI.printMatch(chessMatch, captured);

	}

}
