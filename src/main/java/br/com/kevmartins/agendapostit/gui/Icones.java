package br.com.kevmartins.agendapostit.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class Icones {

    public static final String PLUS = "M12 5v14M5 12h14";
    public static final String CHECK = "m5 12 4 4L19 6";
    public static final String EDIT = "M12 20h9 M16.5 3.5a2.1 2.1 0 0 1 3 3L8 18l-4 1 1-4Z";
    public static final String TRASH = "M3 6h18M8 6V4h8v2M19 6l-1 14H6L5 6M10 10v6M14 10v6";
    public static final String CHEVRON_LEFT = "m15 18-6-6 6-6";
    public static final String CHEVRON_RIGHT = "m9 18 6-6-6-6";

    public static SVGPath criar(String conteudo, Color cor, double tamanho) {
        SVGPath icone = new SVGPath();
        icone.setContent(conteudo);
        icone.setFill(Color.TRANSPARENT);
        icone.setStroke(cor);
        icone.setStrokeWidth(2);
        icone.setStrokeLineCap(StrokeLineCap.ROUND);
        icone.setStrokeLineJoin(StrokeLineJoin.ROUND);

        double escala = tamanho / 24.0;
        icone.setScaleX(escala);
        icone.setScaleY(escala);

        return icone;
    }
}