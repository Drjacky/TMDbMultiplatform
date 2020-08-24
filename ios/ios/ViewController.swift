//
//  MovieItem.swift
//  ios
//
//  Created by Hossein Abbasi on 7/27/20.
//  Copyright © 2020 Hossein Abbasi. All rights reserved.
//


import UIKit
import shared

private var KEYWORD = "avengers"

class ViewController: UIViewController {
    private var _movies: [Movie]?
    private var _isRefreshing = false
    
    lazy var uiRefreshControl: UIRefreshControl = {
        let view = UIRefreshControl()
    
        view.addTarget(self, action: #selector(refresh), for: .valueChanged)
        
        return view
    }()
    
    lazy var uiCollectionView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        
        layout.minimumLineSpacing = 15
        layout.scrollDirection = .vertical
        layout.sectionInset = UIEdgeInsets(top: 15, left: 8, bottom: 8, right: 8)
        
        let marginsAndInsets = layout.sectionInset.left + layout.sectionInset.right + layout.minimumInteritemSpacing * CGFloat(2 - 1)
        let itemWidth = ((UIScreen.main.bounds.size.width - marginsAndInsets) / CGFloat(2)).rounded(.down)
        let itemSize = CGSize(width: itemWidth, height: 270)
        
        layout.itemSize = itemSize
        
        let v = UICollectionView(frame: .zero, collectionViewLayout: layout)
        
        v.backgroundColor = UIColor(named: "ListBackground")
        v.delegate = self
        v.dataSource = self
        v.alwaysBounceVertical = true
        v.refreshControl = uiRefreshControl
        v.translatesAutoresizingMaskIntoConstraints = false
        v.register(MovieItem.self, forCellWithReuseIdentifier: "MovieItem")
        
        return v
    }()
    
    private lazy var _viewModel: MoviesListViewModel<Movie> = {
        let delegate = UIApplication.shared.delegate as! AppDelegate
        let viewModel = MoviesListViewModel<Movie>(mapper: nil)
        
        return viewModel
    }()
    
    private lazy var _binding: ViewModelBinding = {
        return ViewModelBinding()
    }()
    
    deinit {
        _binding.dispose()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        binding()
        
        view.addSubview(uiCollectionView)
        
        NSLayoutConstraint.activate([
            uiCollectionView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            uiCollectionView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            uiCollectionView.topAnchor.constraint(equalTo: view.topAnchor),
            uiCollectionView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
        
        _viewModel.inputs.get(request: KEYWORD)
    }
    
    // MARK - Selector
    @objc func refresh() {
        _viewModel.inputs.get(request: KEYWORD)
    }
    
    // MARK - Private
    private func binding() {
        
        _binding.subscribe(observable: _viewModel.outputs.loading) { [weak self] result in
            guard let strongSelf = self, let loading = result as? Bool else { return }
            
            strongSelf._isRefreshing = loading
            
            if loading {
                strongSelf.uiRefreshControl.beginRefreshing()
            } else {
                strongSelf.uiRefreshControl.endRefreshing()
            }
        }
        
        _binding.subscribe(observable: _viewModel.outputs.result) { [weak self] result in
            guard let strongSelf = self, let list = result as? [Movie] else { return }
            
            strongSelf._movies = list
            strongSelf.uiCollectionView.reloadData()
        }
    }
}

extension ViewController: UICollectionViewDataSource, UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return _movies?.count ?? 0
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "MovieItem", for: indexPath) as! MovieItem
        
        cell.movie = _movies?[indexPath.row]
        
        return cell
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let bottomEdge = scrollView.contentOffset.y + scrollView.frame.size.height;
        
        if (bottomEdge + 200 >= scrollView.contentSize.height && scrollView.contentOffset.y > 0 && !_isRefreshing) {
            _viewModel.inputs.loadMore(request: KEYWORD)
        }
    }
}
