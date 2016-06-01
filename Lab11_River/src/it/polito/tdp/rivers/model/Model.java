package it.polito.tdp.rivers.model;

import it.polito.tdp.rivers.db.RiversDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Model {
	
	public List<River> getAllRivers(){
		RiversDAO dao = new RiversDAO();
		return dao.getAllRivers();
	}
	
	public List<String> popolaTendina(List<River> rivers){
		List<String> lista = new ArrayList<String>();
		for(River r : rivers)
			lista.add(r.getName());
		return lista;
	}
	
		public List<Flow> getAllFlows(List<River> rivers){
		RiversDAO dao = new RiversDAO();
		return dao.getAllFlows(rivers);
	}
		public LocalDate dataPrimaMisura(River r){
			RiversDAO dao = new RiversDAO();
			return dao.dataPrimaMisura(r);
		}
		
		public LocalDate dataUltimaMisura(River r){
			RiversDAO dao = new RiversDAO();
			return dao.dataUltimaMisura(r);
		}
		
		 public int countMisure(River r){
			 RiversDAO dao = new RiversDAO();
			 return dao.countMisure(r);
		 }
			public List<Double> flussiDelFiume(River r){
				 RiversDAO dao = new RiversDAO();
				 return dao.flussiDelFiume(r);
			}
		 public double flussoMedio(List<Double> flussi){
			 Double sum = 0.0;
			 for(Double f : flussi){
				 sum = sum + f;
			 }
			 return sum/flussi.size();
		 }
		 


}
