package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	//Dependência de ChessMatch
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
			
			
			//Peça BRANCA
			//Verificar se a peça movida foi um peão que moveu 2 casas, se for esse o caso, esse peão vai ser marcado como o peão que está vunlerável (Specialmove en passant)
			//Se a posição da peça = 3
			if (position.getRow() == 3) {
				//Mesma linha e coluna do peão
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				//Verificar se a posição da esquerda existe, se tem lá uma peça que é um oponente e se a peça que esta lá é vulnerável
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				
				
				//Mesma linha e coluna do peão
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				//Verificar se a posição da direita existe, se tem lá uma peça que é um oponente e se a peça que esta lá é vulnerável
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
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
			
			//Peça PRETA
			//Verificar se a peça movida foi um peão que moveu 2 casas, se for esse o caso, esse peão vai ser marcado como o peão que está vunlerável (Specialmove en passant)
			//Se a posição da peça = 4
			if (position.getRow() == 4) {
				//Mesma linha e coluna do peão
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				//Verificar se a posição da esquerda existe, se tem lá uma peça que é um oponente e se a peça que esta lá é vulnerável
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				
				
				//Mesma linha e coluna do peão
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				//Verificar se a posição da direita existe, se tem lá uma peça que é um oponente e se a peça que esta lá é vulnerável
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
