package todo.api.api

import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite

@Suite
@SelectClasses(
    CreateTodoApiTest::class,
    GetListTodoApiTest::class,
    UpdateTodoApiTest::class,
    DeleteTodoApiTest::class,
    WebSocketTest::class
)

class TodoApiSuite {
}