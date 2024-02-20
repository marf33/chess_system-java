package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	//Importar tabuleiro
	private Board board;
	
	//Criar jogo
	public ChessMatch() {
		//Tamanho do tabuleiro
		board = new Board(8, 8);
		//Peças no tabuleiro
		initialSetup();
	}
	
	//Retorna uma matriz com peças de xadrez corresponente a uma partida
	public ChessPiece[][] getPieces() {
		//Matriz de peças de xadrez
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColums()];
		//Percorre a matriz do tabuleiro e coloca as posições do tabuleiro
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColums(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	//Colocar nova peça com as posições certas a1,a2...em vez de (0,0),(0,1), etc...
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	//Colocar peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
	}
}
