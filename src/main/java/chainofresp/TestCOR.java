package chainofresp;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/26 10:46
 * @desc : https://stepik.org/lesson/46943/step/2?unit=24990
 */
public class TestCOR {

    public static void main(String[] args) {

        //todo RequestHandler只有一个方法是handle，这个方法接收一个Request，返回一个Request。所以写lambda表达是的时候也是 request -> newRequest


        //Accepts a request and returns new request with data wrapped in the tag <transaction>...</transaction>
        RequestHandler wrapInTransactionTag =
                (req) -> new Request(String.format("<transaction>%s</transaction>", req.getData()));



        //Accepts a request and returns a new request with calculated digest inside the tag <digest>...</digest>
        RequestHandler createDigest =
                (req) -> {
                    String digest = "base64code";
                    return new Request(req.getData() + String.format("<digest>%s</digest>", digest));
                };

        //Accepts a request and returns a new request with data wrapped in the tag <request>...</request>
        RequestHandler wrapInRequestTag =
                (req) -> new Request(String.format("<request>%s</request>", req.getData()));

        //利用上面三个handler，组合成<request><transaction></transaction><digest></digest></request>格式的handler
        RequestHandler commonRequestHandler = wrapInRequestTag.compose(createDigest.compose(wrapInRequestTag));
        //compose意味着括号里面的方法先执行，然后再包裹一层。

        Request helloRequest = commonRequestHandler.handle(new Request("Hello"));
        System.out.println(helloRequest.getData());

        return;
    }
}
