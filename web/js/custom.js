//Three.js 
if ( ! Detector.webgl ) Detector.addGetWebGLMessage();
			var container;
			var camera, scene, renderer;
			var mesh;
			init();
			animate();
			function init() {
				container = document.getElementById( 'hero' );
				//
				camera = new THREE.PerspectiveCamera( 27, window.innerWidth / window.innerHeight, 1, 4000 );
				camera.position.z = 2750;
				scene = new THREE.Scene();
				var segments = 10000;
				var geometry = new THREE.BufferGeometry();
				var material = new THREE.LineBasicMaterial( { vertexColors: THREE.VertexColors } );
				var positions = [];
				var colors = [];
				var r = 800;
				for ( var i = 0; i < segments; i ++ ) {
					var x = Math.random() * r - r / 2;
					var y = Math.random() * r - r / 2;
					var z = Math.random() * r - r / 2;
					// positions
					positions.push( x, y, z );
					// colors
					colors.push( ( x / r ) + 0.5 );
					colors.push( ( y / r ) + 0.5 );
					colors.push( ( z / r ) + 0.5 );
				}
				geometry.addAttribute( 'position', new THREE.Float32BufferAttribute( positions, 3 ) );
				geometry.addAttribute( 'color', new THREE.Float32BufferAttribute( colors, 3 ) );
				geometry.computeBoundingSphere();
				mesh = new THREE.Line( geometry, material );
				scene.add( mesh );
				//
				renderer = new THREE.WebGLRenderer( { antialias: false } );
				renderer.setPixelRatio( window.devicePixelRatio );
				renderer.setSize( window.innerWidth, window.innerHeight );
				renderer.gammaInput = true;
				renderer.gammaOutput = true;
				container.appendChild( renderer.domElement );
				//
				



				//
				window.addEventListener( 'resize', onWindowResize, false );
			}
			function onWindowResize() {
				camera.aspect = window.innerWidth / window.innerHeight;
				camera.updateProjectionMatrix();
				renderer.setSize( window.innerWidth, window.innerHeight );
			}
			//
			function animate() {
				requestAnimationFrame( animate );
				render();
			}
			function render() {
				var time = Date.now() * 0.001;
				mesh.rotation.x = time * 0.25;
				mesh.rotation.y = time * 0.5;
				renderer.render( scene, camera );
			}
