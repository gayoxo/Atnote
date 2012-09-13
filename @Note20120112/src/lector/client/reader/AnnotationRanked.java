package lector.client.reader;

import lector.share.model.Annotation;

public class AnnotationRanked {

	private Annotation AnotacionRankeada;
	private float ranking;
	
	public AnnotationRanked(Annotation A) {
		AnotacionRankeada=A;
				ranking=0f;
				
	}
	
	public void setAnotacionRankeada(Annotation anotacionRankeada) {
		AnotacionRankeada = anotacionRankeada;
	}
	
	public void setRanking(float ranking) {
		this.ranking = ranking;
	}
	
	public Annotation getAnotacionRankeada() {
		return AnotacionRankeada;
	}
	
	public float getRanking() {
		return ranking;
	}
}
