package models

case class User(firstname: String, lastname: String)

object User {

  var list: List[User] = {
    List(
      User("First1", "Last1"),
      User("User2", "Last2")
    )
  }

  def save(user: User) = {
    list = list ::: List(user)
  }
}
