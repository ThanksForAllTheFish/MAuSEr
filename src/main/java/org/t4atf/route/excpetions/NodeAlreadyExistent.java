package org.t4atf.route.excpetions;

public class NodeAlreadyExistent extends RuntimeException
{
  private static final long serialVersionUID = -9013209543589813813L;

  public NodeAlreadyExistent(String message)
  {
    super(message);
  }

}
