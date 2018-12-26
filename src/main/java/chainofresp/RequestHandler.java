package chainofresp;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/26 10:41
 * @desc :
 */

@FunctionalInterface
public interface RequestHandler {

    // The method handle that accept request and returns new request here. It allows to use lambda expressions for creating handlers below.
    Request handle(Request oldRequest);

    //The default method for combining this and other handler single one
    default RequestHandler compose(RequestHandler other){
        return v -> handle(other.handle(v));
    }
    //
}
