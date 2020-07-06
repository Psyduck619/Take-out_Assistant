package takeoutassistant;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import takeoutassistant.control.*;
import takeoutassistant.itf.*;

public class TakeoutAssistantUtil {
    public static IAdminManager adminManager = new AdminManager();
    public static GoodsManager goodsManager = new GoodsManager();
    public static IGoodsTypeManager goodsTypeManager = new GoodsTypeManager();
    public static IRiderManager riderManager = new RiderManager();
    public static ISellerManager sellerManager = new SellerManager();
    public static IUserManager userManager = new UserManager();
    public static IManjianManager manjianManager = new ManjianManager();
    public static ICouponManager couponManager = new CouponManager();
}
