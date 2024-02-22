package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		
		//Todas as posições com o valor falso
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColums()];
				
		Position p = new Position(0, 0);
		
		//Verificar se a cor da minha peça é branca
		if (getColor() == Color.WHITE) {
			
			//Se uma posição acima do peão exisitir e tiver vazia
			p.setValues(position.getRow() - 1, position.getColumn());
			
			//Verficar se o peão pode mover para cima
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
				//Pode mover para cima
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			
			
			//Se duas posições acima do peão exisitir e tiver vazia e moveCount for 0
			p.setValues(position.getRow() - 2, position.getColumn());
			
			//Verificar se a primeira posição está livre
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			
			//Verficar se o peão pode mover para cima
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsApiece(p2) && getMoveCount() == 0) {
				//Pode mover para cima
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			
			//Se uma posição na diagonal esquerda cima do peão exisitir e tiver vazia
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			
			//Verficar se o peão pode mover para cima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				//Pode mover para cima
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			
			
			
			//Se uma posição na diagonal direita cima do peão exisitir e tiver vazia
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			
			//Verficar se o peão pode mover para cima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				//Pode mover para cima
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		else {
			
			//Se uma posição acima do peão exisitir e tiver vazia
			p.setValues(position.getRow() + 1, position.getColumn());
			
			//Verficar se o peão pode mover para cima
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
				//Pode mover para cima
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			
			
			//Se duas posições acima do peão exisitir e tiver vazia e moveCount for 0
			p.setValues(position.getRow() + 2, position.getColumn());
			
			//Verificar se a primeira posição está livre
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			
			//Verficar se o peão pode mover para cima
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsApiece(p2) && getMoveCount() == 0) {
				//Pode mover para cima
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			
			//Se uma posição na diagonal esquerda cima do peão exisitir e tiver vazia
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			
			//Verficar se o peão pode mover para cima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				//Pode mover para cima
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			
			
			
			//Se uma posição na diagonal direita cima do peão exisitir e tiver vazia
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			
			//Verficar se o peão pode mover para cima
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				//Pode mover para cima
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
