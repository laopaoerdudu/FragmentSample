## The ViewModel should create coroutines

`ViewModel` classes should prefer creating coroutines instead of **exposing suspend functions** to perform business logic. 
Suspend functions in the ViewModel can be useful if instead of exposing state using a stream of data, 
only a single value needs to be emitted.

**Create coroutines in the ViewModel**

```
class LatestNewsViewModel(
    private val getLatestNewsWithAuthors: GetLatestNewsWithAuthorsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LatestNewsUiState>(LatestNewsUiState.Loading)
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    fun loadNews() {
        viewModelScope.launch {
            val latestNewsWithAuthors = getLatestNewsWithAuthors()
            _uiState.value = LatestNewsUiState.Success(latestNewsWithAuthors)
        }
    }
}
```

**Prefer observable state rather than suspend functions from the ViewModel**

```
class LatestNewsViewModel(
    private val getLatestNewsWithAuthors: GetLatestNewsWithAuthorsUseCase
) : ViewModel() {
    // DO NOT do this. News would probably need to be refreshed as well.
    // Instead of exposing a single value with a suspend function, news should
    // be exposed using a stream of data as in the code snippet above.
    suspend fun loadNews() = getLatestNewsWithAuthors()
}
```

## The data and business layer should expose suspend functions and Flows

>Classes in those layers should expose `suspend functions` for **one-shot calls** and `Flow` to **notify about data changes**.

```
// Classes in the data and business layer expose
// either suspend functions or Flows
class ExampleRepository {
    suspend fun makeNetworkRequest() { /* ... */ }

    fun getExamples(): Flow<Example> { /* ... */ }
}
```

### Creating coroutines in the business and data layer

```
class GetAllBooksAndAuthorsUseCase(
    private val booksRepository: BooksRepository,
    private val authorsRepository: AuthorsRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun getBookAndAuthors(): BookAndAuthors {
        // In parallel, fetch books and authors and return when both requests
        // complete and the data is ready
        return coroutineScope {
            val books = async(defaultDispatcher) {
                booksRepository.getAllBooks()
            }
            val authors = async(defaultDispatcher) {
                authorsRepository.getAllAuthors()
            }
            BookAndAuthors(books.await(), authors.await())
        }
    }
}
```

`externalScope` should be created and managed by a class that lives longer than the current screen,
it could be managed by the Application class or a ViewModel scoped to a navigation graph.

```
class ArticlesRepository(
    private val articlesDataSource: ArticlesDataSource,
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    // As we want to complete bookmarking the article even if the user moves
    // away from the screen, the work is done creating a new coroutine
    // from an external scope
    suspend fun bookmarkArticle(article: Article) {
        externalScope.launch(defaultDispatcher) {
            articlesDataSource.bookmarkArticle(article)
        }
            .join() // Wait for the coroutine to complete
    }
}
```

## Watch out for exceptions

Unhandled exceptions thrown in coroutines can make your app crash. 
If exceptions are likely to happen, catch them in the body of any coroutines created with `viewModelScope` or `lifecycleScope`.

```
class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    fun login(username: String, token: String) {
        viewModelScope.launch {
            try {
                loginRepository.login(username, token)
                // Notify view user logged in successfully
            } catch (exception: IOException) {
                // Notify view login attempt failed
            }
        }
    }
}
```

Ref:

https://developer.android.com/kotlin/coroutines/coroutines-best-practices












