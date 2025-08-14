package view;

import entity.MovieInterface;
import entity.Movie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MovieComponent extends JPanel {

    // should probably move these constants somewhere more global eventually
    static final int POSTER_WIDTH = 92;
    static final double POSTER_ASPECT_RATIO = 1.5;

    // used to calculate the total height of this from poster height
    static final double HEIGHT_MULTIPLIER = 1.3;

    private final MovieInterface movie;
    private final JLabel poster = new JLabel();
    private final JLabel title = new JLabel();
    private final JLabel year = new JLabel();

    public MovieComponent(MovieInterface movie) {
        super(new GridBagLayout());

        final int posterHeight = (int) Math.round(POSTER_WIDTH * POSTER_ASPECT_RATIO);

        this.movie = movie;

        // style
        this.setPreferredSize(new Dimension(POSTER_WIDTH, (int) Math.round(posterHeight * HEIGHT_MULTIPLIER)));
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        // get poster image from URL (placeholder code)
        final URL url;
        try {
            url = new URL(movie.getPoster());
        }
        catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }

        final ImageIcon posterImage = new ImageIcon(url);
        poster.setIcon(posterImage);
        poster.setPreferredSize(new Dimension(POSTER_WIDTH, (int) Math.round(POSTER_WIDTH * POSTER_ASPECT_RATIO)));

        // get title text (placeholder code currently used)
        title.setText(movie.getTitle());

        // get release year (placeholder code currently used)
        year.setText(String.valueOf(movie.getReleaseDate().getYear()));
        year.setForeground(Color.GRAY);

        this.add(poster, initializePosterConstraints());
        this.add(title, initializeTitleConstraints());
        this.add(year, initializeYearConstraints());
    }

    private GridBagConstraints initializePosterConstraints() {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;

        return c;
    }

    private GridBagConstraints initializeTitleConstraints() {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;

        return c;
    }

    private GridBagConstraints initializeYearConstraints() {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;

        return c;
    }

}
