package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}
	
	//variavel auxiliar pode mover
	
	private boolean canMove(Position position) {
		//Saber se o rei pode mover para a posição
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	//Verificar se na posição 'position' que for informar existe uma torre e se essa torre está apta para o Rook
	private boolean testRookCastling(Position position) {
		
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	//Moviemntos possiveis de um rei
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColums()];
		
		Position p = new Position(0, 0);
		
		//Posição acima do rei
		p.setValues(position.getRow() - 1, position.getColumn());
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Posição abaixo do rei
		p.setValues(position.getRow() + 1, position.getColumn());
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		//Posição à esquerda do rei
		p.setValues(position.getRow(), position.getColumn() - 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			}		
		
		//Posição à direita do rei
		p.setValues(position.getRow(), position.getColumn() + 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//Posição diagonal esquerda cima rei
		p.setValues(position.getRow() -1, position.getColumn() - 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Posição diagonal direita cima rei
		p.setValues(position.getRow() -1, position.getColumn() + 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Posição diagonal esquerda baixo rei
		p.setValues(position.getRow() +1, position.getColumn() - 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Posição diagonal direita baixo rei
		p.setValues(position.getRow() +1, position.getColumn() + 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Verficação se o meu rei pode mover 2 casas para a direita ou 2 para a esquerda (specialmove castling)
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			//Torre pequena -> lado direito do rei
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			//Verificar se a torre está apta para Torre
			if (testRookCastling(posT1)) {
				
				//Verficar se as 2 casas estão vazias
				Position p1  = new Position(position.getRow(), position.getColumn() + 1);
				Position p2  = new Position(position.getRow(), position.getColumn() + 2);
				
				
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			//Torre grande -> lado esquerdo do rei
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
			//Verificar se a torre está apta para Torre
			if (testRookCastling(posT2)) {
				
				//Verficar se as 2 casas estão vazias
				Position p1  = new Position(position.getRow(), position.getColumn() - 1);
				Position p2  = new Position(position.getRow(), position.getColumn() - 2);
				Position p3  = new Position(position.getRow(), position.getColumn() - 3);
				
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		return mat;
	}
	
}
