package use_case.search;

import entity.movie_fields.MovieFieldInterface;
import entity.movie_fields.MovieFieldRegisterInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.movie.MovieDataAccessInterface;

import java.util.*;

import static org.mockito.Mockito.*;

public class SearchInteractorTest {
    private SearchInteractor searchInteractor;
    private MovieFieldRegisterInterface mockFieldRegister;
    private SearchOutputBoundary mockPresenter;
    private MovieDataAccessInterface mockDataAccess;
    private MovieFieldInterface mockField;

    @BeforeEach
    void setUp() {
        mockFieldRegister = mock(MovieFieldRegisterInterface.class);
        mockPresenter = mock(SearchOutputBoundary.class);
        mockDataAccess = mock(MovieDataAccessInterface.class);
        mockField = mock(MovieFieldInterface.class);

        searchInteractor = new SearchInteractor(mockFieldRegister, mockPresenter, mockDataAccess);
    }

    @Test
    void testExecuteWithValidArguments() {
        Map<String, String> searchArgs = Map.of("title", "Inception");
        SearchInputData inputData = new SearchInputData(searchArgs);

//        when(mockFieldRegister.getSearchFields()).thenReturn(Collections.singletonList(mockField));
        when(mockField.getName()).thenReturn("title");
        when(mockField.isValid("Inception")).thenReturn(true);

        searchInteractor.execute(inputData);

        verify(mockField, times(1)).isValid("Inception");
        verify(mockDataAccess, times(1));
        verify(mockPresenter, never()).prepareFailView(anyString());
    }

    @Test
    void testExecuteWithMissingArguments() {
        Map<String, String> searchArgs = new HashMap<>();
        searchArgs.put("genre", "Action");
        SearchInputData inputData = new SearchInputData(searchArgs);

//        when(mockFieldRegister.getSearchFields()).thenReturn(Arrays.asList(mockField, mock(MovieFieldInterface.class)));
        when(mockField.getName()).thenReturn("title");

        searchInteractor.execute(inputData);

        verify(mockField, times(1)).isValid("Action");
    }

    @Test
    void testExecuteWithInvalidArguments() {
        Map<String, String> searchArgs = Map.of("year", "invalid");
        SearchInputData inputData = new SearchInputData(searchArgs);

        when(mockFieldRegister.getSearchFields()).thenReturn(new ArrayList<>());
        when(mockField.getName()).thenReturn("year");
        when(mockField.isValid("invalid")).thenReturn(false);

        searchInteractor.execute(inputData);
        verify(mockPresenter).prepareFailView("Invalid argument for year");
    }
}
