package todo.api

import org.junit.jupiter.api.ClassOrderer.OrderAnnotation
import org.junit.jupiter.api.TestClassOrder
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