//
//  UIImage+Show.swift
//  AgoraEntScenarios
//
//  Created by FanPengpeng on 2022/11/2.
//

import Foundation

extension UIImage {
    @objc static func commerce_sceneImage(name: String) -> UIImage? {
        return sceneImage(name: name, bundleName: "CommerceResource")
    }
}
