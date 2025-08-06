package interface_adapter.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for our Cinedex Application.
 * This class delegates work to a PropertyChangeSupport object for
 * managing the property change events.
 *
 * @param <T> The type of the state object contained in this model.
 */
public class ViewModel<T> {

    // Identifies which View the View Model belongs to (e.g., recommendation, search)
    private final String viewName;

    // Manages Property Changes & Property Change Listeners
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Holds the actual condition/data of type T (for any usecase output)
    private T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    public T getState() {
        return this.state;
    }

    public void setState(T state) {
        this.state = state;
    }

    /**
     * Fires a property changed event for the state of this ViewModel, which
     * allows the user to specify a different propertyName. This can be useful
     * when a class is listening for multiple kinds of property changes.
     * @param propertyName the label for the property that was changed
     */
    public void firePropertyChanged(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    /**
     * Adds a PropertyChangeListener to this ViewModel.
     * @param listener The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
