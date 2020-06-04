import UIKit
import shared

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 200, height: 25))
        label.center = CGPoint(x: 175, y: 295)
        label.textAlignment = .center
        label.font = label.font.withSize(20)
        label.text = CommonKt.HelloWorldMessage()
        view.addSubview(label)
    }


}

