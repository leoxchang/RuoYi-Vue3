import compression from 'vite-plugin-compression'
import type { PluginOption } from 'vite'

interface Env {
    VITE_BUILD_COMPRESS?: string
    [key: string]: any
}

export default function createCompression(env: Env): PluginOption[] {
    const { VITE_BUILD_COMPRESS } = env
    const plugin: PluginOption[] = []
    if (VITE_BUILD_COMPRESS) {
        const compressList = VITE_BUILD_COMPRESS.split(',')
        if (compressList.includes('gzip')) {
            plugin.push(
                compression({
                    ext: '.gz',
                    deleteOriginFile: false
                })
            )
        }
        if (compressList.includes('brotli')) {
            plugin.push(
                compression({
                    ext: '.br',
                    algorithm: 'brotliCompress',
                    deleteOriginFile: false
                })
            )
        }
    }
    return plugin
} 