package models

case class User(name: String)


object User {

  var list: List[User] = {
    List(
      User("User1"),
      User("User2")
    )
  }

  def save(user: User) = {
    list = list ::: List(user)
  }
}




